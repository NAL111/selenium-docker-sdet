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
                bat "docker build -t=nal10/selenium ."
            }
        }
        stage('Push Image') {
            environment {
                DOCKER_HUB = credentials('dockerhub-creds')
            }
            steps {
//                 sh 'docker login -u ${DOCKER_HUB_USR} -p ${DOCKER_HUB_PSW}'
                bat 'docker login -u ${DOCKER_HUB_USR} -p ${DOCKER_HUB_PSW}'
//                 sh "docker push nal/selenium"
                bat "docker push nal/selenium"
            }
        }
    }

    post {
        always {
//             sh "docker logout"
            bat "docker logout"
        }
    }

}
