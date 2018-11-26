FROM ubuntu:14.04

RUN yarn install

EXPOSE 3000

CMD ["yarn", "start"]