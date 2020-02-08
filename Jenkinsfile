def VERSION = "NULL";
pipeline {
    agent {
        docker {
            label 'docker'
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2'
        }
    }
    stages {
        stage('Print') {
            steps {
                echo 'Test2'
                sh 'printenv'
                echo 'Pulling...' + env.GIT_BRANCH
            }
        }
        stage('Build') {
            steps {
                sh 'mvn -B clean install'
            }
        }
        stage('Install') {
            steps {
                sh 'mvn install'
            }
        }
        stage('Archive') {
            steps {
                archiveArtifacts artifacts: '**/target/*.jar'
            }
        }
        /*stage('Set BuildNumber') {
            steps {
                script {
                    VERSION = readMavenPom().getVersion();
                }
                echo "Get version: ${VERSION}"
                sh "mvn versions:set -DnewVersion=${VERSION}.$BUILD_NUMBER"
            }
        }*/
        stage('Deploy') {
            steps {
                configFileProvider([configFile(fileId: 'afe25550-309e-40c1-80ad-59da7989fb4e', variable: 'MAVEN_GLOBAL_SETTINGS')]) {
                    sh 'mvn -gs $MAVEN_GLOBAL_SETTINGS deploy'
                }
            }
        }
    }
}
