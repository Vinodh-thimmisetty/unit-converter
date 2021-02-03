FROM alpine/git AS GIT_REPO
WORKDIR /app
RUN git clone https://github.com/Vinodh-thimmisetty/ayoholdings.git

FROM node:12.19.0 as NODEJS
WORKDIR /app/ayoholdings/conversion-ui
COPY --from=GIT_REPO /app/ayoholdings /app/ayoholdings
RUN npm install
COPY . .
EXPOSE 4200
CMD npm start

# docker pull vinodh5052/conversion_ui
# docker run -d -p 4200:4200 conversion_ui