
if ($#ARGV < 1) {
  print "Usage: binderVersion.pl VER FILE {FILE, FILE}\n";
  exit;
}

$V= $ARGV[0];
# Trim -SNAPSHOT
$V =~ s/-SNAPSHOT//;

print "VER:${V}\r\n";
shift(@ARGV);

sub replace () {
  my $filename = $_[0];

  if(-s $filename) {
    print "Processing [" . $filename . "]\r\n";

    my $original = "$filename.original";
    
    rename($filename, $original);
    open(OUT, ">$filename");
    open(IN, "$original");
    
    while(<IN>) {
      if(/VERSION\s+=\s+".*";/) {
        s/VERSION\s+=\s+".*";/VERSION = "${V}";/;
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
