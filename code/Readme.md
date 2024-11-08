## How to Run?

### 1. Frontend
```
cd code/frontend
```
#### 1.1 Make sure you have npm or node installed.
```
npm -v 
node -v
```
#### 1.2 Install all the dependencies

```
npm install
```

#### 1.3 Start the front-end App

```
export NODE_OPTIONS=--openssl-legacy-provider
npm run serve
```

#### 1.4 Open your browser

```
http://localhost:8080
```

#### 1.5 Compile and Build dist. (Production only)

```
npm run build
```

> The Compiled files under: ./dist

### 2. Copy Front-end App to Backend (CORS)

```
npm run build
```
> The Compiled files under: ./dist


### 2. Copy Fron-end App to Backend (CORS)

```
cp -r ./dist/* ../backend/src/main/resource/static/
```

### 3. Backend

> Prerequisite:

-   Database creation required.
-   JDK
-   Maven

#### 3.1 Use any IDE to open the code:

Here, we recommend **IntelliJ IDEA**

#### 3.1 Use any IDE for your Development:

```
cd backend

mvn clean install -DskipTests

```

#### 3.2 Start Spring Boot Application (For Dev.)

> Main Class:

```
edu.bu.cs673.secondhand.Application
```

#### 3.3 Compile (For Prod.)

```
mvn clean package -DskipTests
```

> Note: The compiled Jar be under: src/main/resource/bu-secondhand-1.0.0.jar
> **Download Our snapshot**: [Download](https://drive.google.com/file/d/1MGHmqFn9JVJDV9i4CGHPK_WyYMrfZ3wQ/view?usp=sharing)

#### 3.4 Deploy and Run (For Prod.)

```
java -jar bu-secondhand-1.0.0.jar
```

> Open Browser: http://localhost:8080
