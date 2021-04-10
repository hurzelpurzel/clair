pipeline{
    
    agent {
          docker {
            image 'nordri/clair-scanner'
            args '-u root --privileged -v /var/run/docker.sock:/var/run/docker.sock --net clair_default'
          }
    }
    parameters {
        string(name: 'testant', defaultValue: 'spiderpig5371/discordbot:latest', description: 'Name & tag of image to scan')
    }
  
   
   stages{
      
       /*stage("show"){
           steps{
           sh "env"
           }
       } */   
       stage ('Security scanner') {
           
           steps{
               script{
            def IP = sh returnStdout :true, script: '''ip r | tail -n1 | awk '{ print $9 }' '''
            IP=IP.trim()
            println IP
       
             sh "/clair-scanner --ip ${IP} --clair=http://clair:6060 --threshold='Critical'  --report='report.json' ${params.testant} "
               }
           archiveArtifacts artifacts: 'report.json', followSymlinks: false
           }
               
           }
   }
}