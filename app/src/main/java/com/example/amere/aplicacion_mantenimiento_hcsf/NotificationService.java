package com.example.amere.aplicacion_mantenimiento_hcsf;


import android.app.Notification;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
public class NotificationService extends FirebaseMessagingService {
int cont;
String estado;
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
                cont++;
                if(remoteMessage.getData().get("estado")!=null) {
                    estado = remoteMessage.getData().get("estado");
                    if (estado.equals("En proceso")) {
                        administrador_notificaciones adm = new administrador_notificaciones(NotificationService.this);
                        Notification.Builder nb = adm.createNotification(remoteMessage.getFrom(), remoteMessage.getData().get("estado"), false);
                        adm.getManager().notify(cont, nb.build());
                    }
                }
                if(remoteMessage.getData().get("id")!=null)
                {
                    administrador_notificaciones adm = new administrador_notificaciones(NotificationService.this);
                    Notification.Builder nb = adm.createNotification(remoteMessage.getFrom(), remoteMessage.getData().get("id"), false);
                    adm.getManager().notify(cont, nb.build());
                }

        }
    }
}
