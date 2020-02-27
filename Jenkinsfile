pipeline {
    agent any
    tools {
        maven 'Maven3'
        jdk 'Java8'
    }
    options {
        buildDiscarder logRotator(numToKeepStr: '10')
    }
    stages {
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
    }
}
