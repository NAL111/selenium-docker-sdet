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
//                 bat "docker build -t=nal10/selenium ."
                bat "docker build -t=nal10/selenium:latest ."
            }
        }

        stage('Push Image') {
            environment {
                DOCKER_HUB = credentials('dckrhb-crds')
            }
            steps {
//                 sh 'docker login -u ${DOCKER_HUB_USR} -p ${DOCKER_HUB_PSW}'
//                 bat 'docker login -u %DOCKER_HUB_USR% -p %DOCKER_HUB_PSW%'
                bat 'echo %DOCKER_HUB_PSW% | docker login -u %DOCKER_HUB_USR% --password-stdin'
//                 sh "docker push nal/selenium"
//                 bat "docker push nal10/selenium"
                bat 'docker push nal10/selenium:latest'
                bat "docker tag nal10/selenium:latest nal10/selenium:${env.BUILD_NUMBER}"
                bat "docker push nal10/selenium:${env.BUILD_NUMBER}"
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
