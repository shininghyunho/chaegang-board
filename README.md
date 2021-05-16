# 스프링 부트를 활용한 게시판
## [메인 페이지](http://ec2-15-165-143-80.ap-northeast-2.compute.amazonaws.com/)
## 사용 스택
### 구현
- spring-boot
- jpa
- mustache (프론트엔드)
- oauth (구글, 네이버)
- UnitTest (JUnit)
### CI&CD
- [ec2](https://aws.amazon.com/ko/) (인스턴스)
- [travis-ci](https://travis-ci.com/) (빌드)
- S3 (빌드파일 저장)
- CodeDeploy (배포)
- IAM (권한 저장)
- aws rds (디비)
- nginx (무중단배포)
## 참고자료
스프링 부트와 AWS로 혼자 구현하는 웹 서비스 구현해보기(with [jojoldu](https://github.com/jojoldu))
