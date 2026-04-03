pipeline {

    agent any

    stages {

        stage('Build Jar') {
            steps {
//                 sh "mvn clean package -DskipTests"
                bat "mvn clean package -DskipTests"
            }
        }

        stage('Build Image') {
            steps {
//                 sh "docker build -t=nal/selenium ."
                bat "docker build -t=nal/selenium ."
            }
        }
        stage('Push Image') {
            steps {
                sh "docker push nal/selenium"
                bat "docker push nal/selenium"
            }
        }
    }
    
}
