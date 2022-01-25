mvn versions:set -DgenerateBackupPoms=false -DnewVersion=${VERSION_NUMBER} 

edit slf4j-log4j12 version 

mvn clean
mvn install

mvn deploy -P javadocjar,sign-artifacts -Dgpg.passphrase=passwd

#uncomment diffie-hellman support in /etc/ssh/sshd_config

mvn site:deploy -N # with Java 8!!!

git tag -m "tagging" -a v_${VERSION_NUMBER}
git push --tags

release version and add next version on jira
