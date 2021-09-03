

//$.getScript("https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js",
//   function() {
//       jQuery("#right").append('<ins class="adsbygoogle" \
//          style="display:block" \
//          data-ad-client="ca-pub-7471410671306824" \
//          data-ad-slot="7413932813" \
//          data-ad-format="auto" \
//          data-full-width-responsive="true"></ins> \
//        <script> \
//         (adsbygoogle = window.adsbygoogle || []).push({}); \
//        </script>');
//       }
//  )
//  .fail(
//      function() {
//          document.write("<p>Adblock detected</p>");
//     }
//  );
//

function addBlockDetected() {
    jQuery("#right").append('<p class="big">If using SLF4J saves you hours of work, please whitelist this site in your \
add blocker.</p>');
}

document.write('      <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7471410671306824" \
  onerror="addBlockDetected();"  ></script>');

//      <!-- SLF4J -->
jQuery("#right").append(' <ins class="adsbygoogle" \
           style="display:block" \
           data-ad-client="ca-pub-7471410671306824" \
           data-ad-slot="7413932813" \
           data-ad-format="auto"></ins> \
      <script> \
       (adsbygoogle = window.adsbygoogle || []).push({});\
      </script>');

