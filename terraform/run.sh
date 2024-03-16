#!/bin/bash

# terraform 테스트용 스크립트입니다.

# Docker Engine 실행 상태에서 TWTW/terraform 디렉토리에서 실행해주세요. 
# init, plan, apply, destroy 등을 인자로 넘겨주세요.
# 예시: sh run.sh init

docker run -it -v "$(pwd)":/app -w /app hashicorp/terraform:latest $1
