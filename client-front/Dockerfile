FROM node:9.6.1

RUN npm update

RUN mkdir /usr/src/app
WORKDIR /usr/src/app

RUN npm install -g @angular/cli@6.0.0

RUN npm update

COPY . /usr/src/app

CMD ng serve --host 0.0.0.0 --port 4200
