FROM index.alauda.cn/alaudaorg/eirene:base

EXPOSE 18080

# Adding compiled stuff
COPY supervisord.conf /etc/supervisord.conf
COPY eirene.conf /etc/supervisor/conf.d/eirene.conf
RUN mkdir -p /var/log/supervisor/ /var/log/mathilde/ /eirene/

COPY /starlink-*.jar /eirene/
COPY *.sh /
COPY *.py /
RUN chmod +x /*.sh

CMD ["/eirene.sh"]



