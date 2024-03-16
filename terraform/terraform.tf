resource "aws_default_vpc" "vpc_network" {
  tags                 = merge(var.tags, {})
  enable_dns_support   = true
  enable_dns_hostnames = true
}

resource "aws_internet_gateway" "internet_gw" {
  vpc_id = aws_default_vpc.vpc_network.id
  tags   = merge(var.tags, {})
}

resource "aws_subnet" "private-subnet-a" {
  vpc_id            = aws_default_vpc.vpc_network.id
  tags              = merge(var.tags, {})
  cidr_block        = var.private_subnet_cidr[0]
  availability_zone = "ap-northeast-2a"
}

resource "aws_subnet" "private-subnet-c" {
  vpc_id            = aws_default_vpc.vpc_network.id
  tags              = merge(var.tags, {})
  cidr_block        = var.private_subnet_cidr[1]
  availability_zone = "ap-northeast-2c"
}

resource "aws_subnet" "public-subnet-c" {
  vpc_id            = aws_default_vpc.vpc_network.id
  tags              = merge(var.tags, {})
  cidr_block        = var.public_subnet_cidr
  availability_zone = "ap-northeast-2b"
}

resource "aws_elb" "elb" {
  tags                      = merge(var.tags, {})
  cross_zone_load_balancing = true

  availability_zones = [
    "ap-northeast-2a",
    "ap-northeast-2b",
  ]

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
  vpc_id = aws_default_vpc.vpc_network.id
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
      var.public_subnet_cidr,
    ]
  }
  ingress {
    to_port   = 61613
    protocol  = "tcp"
    from_port = 61613
    cidr_blocks = [
      var.public_subnet_cidr,
    ]
  }
  ingress {
    to_port   = 5672
    protocol  = "tcp"
    from_port = 5672
    cidr_blocks = [
      var.public_subnet_cidr,
    ]
  }
}

resource "aws_security_group" "security-group-c" {
  vpc_id = aws_default_vpc.vpc_network.id
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
      var.public_subnet_cidr,
    ]
  }
}

resource "aws_db_instance" "db_instance" {
  username          = "admin"
  timezone          = "Asia/Seoul"
  tags              = merge(var.tags, {})
  port              = 3306
  password          = var.db_password
  instance_class    = "db.m1.micro"
  engine            = "mysql"
  db_name           = "TWTW"
  availability_zone = "ap-northeast-2b"
}

resource "aws_mq_broker" "mq_broker" {
  tags        = merge(var.tags, {})
  engine_type = "rabbitmq"
  engine_version = "3.8.6"
  host_instance_type = "mq.t3.micro"
  broker_name = "rabbitmq"

  user {
    username = var.rabbitmq.username
    password = var.rabbitmq.password
  }

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
}

resource "aws_launch_template" "asg" {
  name          = "asg"
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
    id      = aws_launch_template.asg.id
    version = "$Latest"
  }

  vpc_zone_identifier = [
    aws_subnet.private-subnet-a.id,
    aws_subnet.private-subnet-c.id,
  ]
}