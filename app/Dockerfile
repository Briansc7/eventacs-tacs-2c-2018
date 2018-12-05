\
#FROM ubuntu:14.04
#RUN apt-get update
#RUN apt-get install -y yarn
#RUN yarn install 
#EXPOSE 3000
#CMD ["yarn", "start"]

#FROM alpine/git
#workdir /app
#ADD ./ /app
#RUN git clone https://github.com/sebastianchaves/eventacs-tacs-2c-2018.git
#FROM node:6.11.0 

FROM node:8
WORKDIR /app
ADD ./ /app
EXPOSE 3000 
#CMD ["yarn", "install"]
RUN npm install react-scripts -y
RUN yarn
CMD ["yarn", "start"]
