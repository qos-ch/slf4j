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

function echoRunAndCheck () { # echo and then run the command
  echo $1
  $1
  ret=$?
  if test "$ret" != "0";
  then
     echo Failed command: $1 
     exit 1;
  else echo Succussful run: $1
  fi
}

echoRunAndCheck "$MVN clean"

echoRunAndCheck "$MVN install"

#echoRunAndCheck "$MVN site:site"

echoRunAndCheck "$MVN javadoc:aggregate"

echoRunAndCheck "$MVN jxr:aggregate"

echoRunAndCheck "$MVN assembly:single"

if [ ! -z "$PASS"  ]
then
  echoRunAndCheck "$MVN deploy -P javadocjar,sign-artifacts -Dgpg.passphrase=$PASS"
fi

#$MVN site:deploy -N # with Java 8!!!
#checkExit "mvn site:deploy -N"

#git tag -m "tagging" -a v_${VERSION_NUMBER}
#git push --tags

#release version and add next version on jira

echo Full Success
