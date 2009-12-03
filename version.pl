
if ($#ARGV < 1) {
  print "Usage: version.pl VER FILE {FILE, FILE}\n";
  exit;
}

$V=$ARGV[0];
print "VER:'${V}'\r\n";
shift(@ARGV);

sub replace () {
  my $filename = $_[0];

  if(-s $filename) {
    print "Processing [" . $filename . "]\r\n";

    my $original = "$filename.original";
    
    rename($filename, $original);
    open(OUT, ">$filename");
    open(IN, "$original");
    
    my $hitCount=0;
    while(<IN>) {
      if($hitCount == 0 && /<version>.*<\/version>/) {
        s/<version>.*<\/version>/<version>${V}<\/version>/;
        $hitCount++;
      } 
      print OUT;
    }
    close(IN);
    close(OUT);
    unlink($original);
  } else {
    print "File [" . $filename . "] does not exist\r\n" 
  }
}

foreach $ARG (@ARGV) {
  do replace($ARG);
}



