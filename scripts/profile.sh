#!/usr/bin/env bash

# 쉬고 있는 profile 찾기: real1이 사용중이면 real2는 쉬고, real2가 사용중이면 real1이 쉼

function find_idle_profile(){
  # 현재 엔진엑스가 바라보고있는 스프링부트
  RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)

  if [ ${RESPONSE_CODE} -ge 400 ] # 반환값이 400 보다 크면 에러
  then
    # 에러 발생시 현재 profile을 real2로
    CURRENT_PROFILE=real2
  else
    CURRENT_PROFILE=$(curl -s http://localhost/profile)
  fi

  if [ ${CURRENT_PROFILE} == real1 ] # 현재 profile 확인
  then
    # 엔진엑스와 연결되지 않은 profile
    IDLE_PROFILE=real2
  else
    IDLE_PROFILE=real1
  fi

  # bash 용 return 값
  echo "${IDLE_PROFILE}"
}

function find_idle_port(){
  IDLE_PROFILE=$(find_idle_profile)

  if [ ${IDLE_PROFILE} == real1 ]
  then
    # real1 port
    echo "8081"
  else
    # real2 port
    echo "8082"
  fi
}