#mvn versions:set -DgenerateBackupPoms=false -DnewVersion=${VERSION_NUMBER} 

MVN='/java/maven-3.5.2//bin/mvn'

PASS=$1
echo $PASS

function checkExit(){
    if test "$?" != "0"; then
      echo Command $1 exited with abnormal status
      exit 1;
    else echo $?
    fi
}

function echoThenRun () { # echo and then run the command
  echo $1
  $1
  ret=$?
  echo $ret
  return $ret
}

$MVN clean
checkExit "mvn clean"


$MVN install
checkExit "mvn install"


$MVN site:site
checkExit "mvn site:ste"


$MVN assembly:single
checkExit "mvn assembly:single"


$MVN deploy -P javadocjar,sign-artifacts -Dgpg.passphrase=$PASS
checkExit "mvn deploy -P javadocjar,sign-artifacts -Dgpg.passphrase=xxx"

#$MVN site:deploy -N # with Java 8!!!
#checkExit "mvn site:deploy -N"

#git tag -m "tagging" -a v_${VERSION_NUMBER}
#git push --tags

#release version and add next version on jira
