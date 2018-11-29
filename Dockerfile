#FROM ubuntu:14.04
#RUN mvn package
#RUN mv /etc/alternatives/java /etc/alternatives/java8
#RUN apt-get update -y && apt-get install maven -y
#FROM alpine/git
#workdir /app
#RUN git clone https://github.com/sebastianchaves/eventacs-tacs-2c-2018.git

FROM maven:3.5-jdk-8-alpine
WORKDIR /app
ADD ./ /app
#COPY --from=0 /app/eventacs-tacs-2c-2018  /app
#RUN  mvn install

EXPOSE 9000
CMD ["mvn", "clean", "compile", "exec:java"]
