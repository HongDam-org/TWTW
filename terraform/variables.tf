variable "vpc_cidr_block" {
  description = "The CIDR block for the VPC"
  type        = string
}

variable "public_subnet_cidr" {
  description = "The CIDR block for the public subnet"
  type        = string
}

variable "private_subnet_cidr" {
  description = "List of CIDR blocks for the private subnets"
  type        = list(string)
}

variable "ami" {
  description = "The AMI ID to use for the instances"
  type        = string
}

variable "db_password" {
  description = "The password for the RDS database instance"
  type        = string
}

variable "tags" {
  description = "A map of tags to add to all resources"
  type        = map(string)
}

variable "rabbitmq" {
  description = "RabbitMQ credentials"
  type        = map(string)
}

variable "elb_protocol" {
  description = "List of protocols for the ELB"
  type        = list(string)
}

variable "elb_port" {
  description = "List of ports the ELB listens on"
  type        = list(number)
}
