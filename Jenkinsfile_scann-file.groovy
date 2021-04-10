node{
    def images =[]
    stage("get images"){
      def img = sh returnStdout: true, script: 'docker images  --format "{{json .Repository }}:{{json .Tag }}"'
      images=img.split("\n")     
      //println images    
        
    }
    
    stage("start scann"){
        images.each{
            println "Start scan of ${it}"
            build wait: false, job: 'clair-scann', parameters: [string(name: 'testant', value: it)]
        }
    }
    
}