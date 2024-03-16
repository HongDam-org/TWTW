resource "aws_vpc" "vpc_network" {
  tags                 = merge(var.tags, {})
  enable_dns_support   = true
  enable_dns_hostnames = true
  cidr_block           = "10.0.0.0/16"
}

resource "aws_subnet" "private-subnet-a" {
  vpc_id            = aws_vpc.vpc_network.id
  tags              = merge(var.tags, {})
  cidr_block        = cidrsubnet(aws_vpc.vpc_network.cidr_block, 8, 1)
  availability_zone = "ap-northeast-2a"
}

resource "aws_subnet" "private-subnet-c" {
  vpc_id            = aws_vpc.vpc_network.id
  tags              = merge(var.tags, {})
  cidr_block        = cidrsubnet(aws_vpc.vpc_network.cidr_block, 8, 2)
  availability_zone = "ap-northeast-2c"
}

resource "aws_subnet" "public-subnet-c" {
  vpc_id            = aws_vpc.vpc_network.id
  tags              = merge(var.tags, {})
  cidr_block        = cidrsubnet(aws_vpc.vpc_network.cidr_block, 8, 3)
  availability_zone = "ap-northeast-2b"
}

resource "aws_internet_gateway" "igw" {
  vpc_id = aws_vpc.vpc_network.id
}

resource "aws_route_table" "r" {
  vpc_id = aws_vpc.vpc_network.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.igw.id
  }
}

resource "aws_route_table_association" "a" {
  subnet_id      = aws_subnet.public-subnet-c.id
  route_table_id = aws_route_table.r.id
}

resource "aws_elb" "elb" {
  tags                      = merge(var.tags, {})
  cross_zone_load_balancing = true

  listener {
    lb_protocol       = var.elb_protocol[0]
    lb_port           = var.elb_port[0]
    instance_protocol = var.elb_protocol[0]
    instance_port     = var.elb_port[0]
  }

  listener {
    lb_protocol       = var.elb_protocol[1]
    lb_port           = var.elb_port[1]
    instance_protocol = var.elb_protocol[1]
    instance_port     = var.elb_port[1]
  }

  subnets = [
    aws_subnet.public-subnet-c.id
  ]
}

resource "aws_instance" "instance-a" {
  tags                        = merge(var.tags, {})
  subnet_id                   = aws_subnet.private-subnet-a.id
  instance_type               = "t3a.medium"
  availability_zone           = "ap-northeast-2a"
  associate_public_ip_address = false
  ami                         = var.ami

  vpc_security_group_ids = [
    aws_security_group.security-group-a.id,
  ]
}

resource "aws_instance" "instance-c" {
  tags              = merge(var.tags, {})
  subnet_id         = aws_subnet.private-subnet-c.id
  instance_type     = "t3a.medium"
  availability_zone = "ap-northeast-2c"
  associate_public_ip_address = false
  ami                         = var.ami

  provisioner "remote-exec" {
    inline = [
      "sudo apt update",
      "sudo apt install -y docker.io",
      "sudo systemctl start docker",
      "sudo systemctl enable docker",
      "sudo apt install docker-compose",
      "sudo usermod -aG docker ubuntu"
    ]
  }
}

resource "aws_security_group" "security-group-a" {
  vpc_id = aws_vpc.vpc_network.id
  tags   = merge(var.tags, {})

  egress {
    to_port   = 0
    protocol  = "tcp"
    from_port = 0
    cidr_blocks = [
      aws_subnet.private-subnet-a.cidr_block,
    ]
  }

  ingress {
    to_port   = 80
    protocol  = "tcp"
    from_port = 80
    cidr_blocks = [
       cidrsubnet(aws_vpc.vpc_network.cidr_block, 8, 3)
    ]
  }
  ingress {
    to_port   = 61613
    protocol  = "tcp"
    from_port = 61613
    cidr_blocks = [
       cidrsubnet(aws_vpc.vpc_network.cidr_block, 8, 3)
    ]
  }
}

resource "aws_security_group" "security-group-c" {
  vpc_id = aws_vpc.vpc_network.id
  tags   = merge(var.tags, {})

  egress {
    to_port   = 0
    protocol  = "tcp"
    from_port = 0
    cidr_blocks = [
      aws_subnet.private-subnet-c.cidr_block,
    ]
  }

  ingress {
    to_port   = 80
    protocol  = "tcp"
    from_port = 80
    cidr_blocks = [
       cidrsubnet(aws_vpc.vpc_network.cidr_block, 8, 3),
    ]
  }
}

resource "aws_subnet_group" "twtw_subnet_group" {
  name       = "twtw-subnet-group"
  subnet_ids = [aws_subnet.private-subnet-a.id, aws_subnet.private-subnet-c.id]

  tags = {
    Name = "TWTW subnet group"
  }
}

resource "aws_db_instance" "db_instance" {
  username          = "admin"
  timezone          = "Asia/Seoul"
  tags              = merge(var.tags, {})
  port              = 3306
  password          = var.db_password
  instance_class    = "db.m5d.large"
  engine            = "mysql"
  db_name           = "TWTW"
  availability_zone = "ap-northeast-2b"
  db_subnet_group_name = aws_subnet_group.twtw_subnet_group.name
  allocated_storage = 20
}

resource "aws_mq_broker" "mq_broker" {
  tags        = merge(var.tags, {})
  engine_type = "rabbitmq"
  engine_version = "3.8.6"
  host_instance_type = "mq.t3.micro"
  broker_name = "rabbitmq"
  publicly_accessible = false

  user {
    username = var.rabbitmq.username
    password = var.rabbitmq.password
  }

  subnet_ids = [
    aws_subnet.private-subnet-a.id,
  ]

  security_groups    = [
        aws_security_group.security-group-a.id, 
        aws_security_group.security-group-c.id
    ]
}

resource "aws_elasticache_cluster" "elasticache_cluster" {
  tags              = merge(var.tags, {})
  port              = 6379
  engine            = "redis"
  cluster_id        = "twtw-redis-cluster"
  availability_zone = "ap-northeast-2b"
  node_type         = "cache.t2.micro"
  num_cache_nodes   = 1
}

resource "aws_launch_template" "asg_template" {
  name_prefix   = "lt-twtw-"
  image_id      = var.ami
  instance_type = "t3a.medium"
}


resource "aws_autoscaling_group" "autoscaling_group" {
  name_prefix               = "server_launch_config"
  min_size                  = 2
  max_size                  = 4
  health_check_type         = "ELB"
  health_check_grace_period = 100
  force_delete              = true

  launch_template {
    id      = aws_launch_template.asg_template.id
    version = "$Latest"
  }

  vpc_zone_identifier = [
    aws_subnet.private-subnet-a.id,
    aws_subnet.private-subnet-c.id,
  ]
}