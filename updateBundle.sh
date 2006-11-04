
VERSION=1.1.0-RC0
echo $VERSION

MVN=/java/maven-2.0.4/bin/mvn

update() {
 MODULE=$1
 pushd $MODULE
 $MVN repository:bundle-create 
 echo Maven exited with $?
 if [ $? != 0 ]
 then
   echo mvn command failed
   exit 1;
 fi
 scp target/$MODULE-$VERSION-bundle.jar pixie:/var/www/www.slf4j.org/htdocs/dist/bundles/
 popd
}

update slf4j-api
update slf4j-archetype
update slf4j-jcl
update slf4j-jdk14
update slf4j-log4j12
update slf4j-nop
update slf4j-simple
update jcl104-over-slf4j
update log4j-over-slf4j