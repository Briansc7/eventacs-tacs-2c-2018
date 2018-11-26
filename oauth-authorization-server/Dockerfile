FROM ubuntu:14.04

RUN mvn package

EXPOSE 9001

CMD ["mvn", "clean", "compile", "exec:java"]