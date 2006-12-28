
VER=$1
echo "Will use version $VER"

find . -name "pom.xml" |xargs perl version.pl $VER 
perl version.pl $VER ./slf4j-site/src/site/site.xml