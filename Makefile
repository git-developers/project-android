NOW := $(shell /bin/date "+%Y-%m-%d . %H:%M:%S")
AUTHOR_EMAIL := "jafeth.bendezu.a@gmail.com"
AUTHOR_NAME := "Jafeth Bendezu"
PROJECT_NAME := tesis-mobile

save:
	git add .
	git config --global user.email $(AUTHOR_EMAIL)
	git config --global user.name $(AUTHOR_NAME) --replace-all
	git commit -m "modificaciones hechas el: $(NOW)"
ifeq ($(http), 1)
	git push origin_http master
else
	git push origin master
endif

adb:
	export ANDROID_HOME=/home/jafeth/Android/Sdk
	export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools
	sleep 2;
	adb kill-server
	adb start-server
