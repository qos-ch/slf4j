
# Javadoc

#JDK8 - mvn site:site
#rscpSLF4J apidocs/ 

# JDK 11+
# adding the following 
#mvn -Ddoclint=none -Dadditionalparam=-Xdoclint:none  javadoc:aggregate


#mvn versions:set -DgenerateBackupPoms=false -DnewVersion=${VERSION_NUMBER} 

MVN='/java/maven-3.5.2//bin/mvn'

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
  else echo Successful run: $1
  fi
}

echoRunAndCheck "$MVN clean"

echoRunAndCheck "$MVN install"

#echoRunAndCheck "$MVN site:site"

#echoRunAndCheck "$MVN javadoc:aggregate"

#echoRunAndCheck "$MVN jxr:aggregate"


if [ ! -z "$PASS"  ]
then
    # WARNING deploying without cleaning may leave stale MANIFEST files    
    export GPG_TTY=$(tty)
    echoRunAndCheck "$MVN deploy -P javadocjar,sign-artifacts"
fi


git tag -m "tagging" -a v_${VERSION_NUMBER}
git push --tags

#Update release version and add next version on jira



echo Full Success
