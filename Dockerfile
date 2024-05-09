# Specify Base Image
FROM node:18-alpine
 
# Work Directory
WORKDIR /usr/app

COPY package.json ./

# Install Dependencies
RUN npm install

COPY ./ ./
 
# Startup Command
CMD ["npm", "start"]