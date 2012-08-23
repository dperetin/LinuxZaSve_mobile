Linux za sve Android aplikacija
========================

Android aplikacija za linuxzasve.com

Najsvježiji build
=================

Najsvježiji build appa se može preuzeti `ovdje <https://github.com/dperetin/LinuxZaSve_mobile/raw/master/LinuxZaSve_mobile/bin/LinuxZaSve_mobile.apk>`_



.. image:: http://chart.googleapis.com/chart?cht=qr&chs=150x150&choe=UTF-8&chld=H&chl=http://goo.gl/f7cYs

Kako buildati
============

App koristi ActionBarSherlock: https://github.com/JakeWharton/ActionBarSherlock

Potrebno je imati instalirano sljedeće:

* `Eclipse <http://www.eclipse.org/downloads/packages/eclipse-classic-42/junor>`_
* `Android SDK <http://developer.android.com/sdk/index.html>`_

    - Obavezno instalirati platformu Android 4.0 (API 14)
    - Obavezno instalirati platformu Android 4.03 (API 15)
    - Instalirati Android Support Library

* `ADT plugin <http://developer.android.com/sdk/installing/installing-adt.html>`_

Upute za buildanje
-----------------

1. Skinuti ActionBarSherlock ::

    git clone https://github.com/JakeWharton/ActionBarSherlock.git

2. Napraviti novi Eclipse projekt: ::

    File -> New -> Android Project From Existing Code

    Za root folder odabrati ActionBarSherlock/library

3. Desni klik -> Properies -> Java Compiler

    Staviti compiler compliance level na 1.6

4. Skinuti source ovog appa ::

    git clone https://github.com/dperetin/LinuxZaSve_mobile.git

5. Napraviti novi Eclipse projekt

    File -> New -> Android Project From Existing Code

    Za root folder odabrati LinuxZaSve_mobile

6. Desni klik -> Properties -> Android -> Add library
