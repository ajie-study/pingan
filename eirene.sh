#!/usr/bin/env sh

sync
chmod +x /*.sh /eirene/*.jar

sync
supervisord --nodaemon --configuration /etc/supervisord.conf