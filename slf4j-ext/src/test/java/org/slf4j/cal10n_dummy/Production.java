package org.slf4j.cal10n_dummy;

import ch.qos.cal10n.LocaleData;
import ch.qos.cal10n.Locale;
import ch.qos.cal10n.BaseName;

@BaseName("production")
@LocaleData( { @Locale("en_UK"), @Locale("fr") })
public enum Production  {
  APPLICATION_STARTED,
  APPLICATION_STOPPED,
  DB_CONNECTION,
  DB_CONNECTION_FAILURE;
}