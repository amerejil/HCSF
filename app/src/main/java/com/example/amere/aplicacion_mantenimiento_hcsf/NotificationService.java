package com.example.amere.aplicacion_mantenimiento_hcsf;


import android.app.Notification;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationService extends FirebaseMessagingService {

    data_task task;
    String topics;
    String estado;
    public void onMessageReceived(RemoteMessage remoteMessage) {
         task=new data_task();
         estado=new String();
        task.setArea(remoteMessage.getData().get("area"));
        task.setUbicacion(remoteMessage.getData().get("ubicacion"));
        task.setAtencion(remoteMessage.getData().get("atencion"));
        task.setTipo(remoteMessage.getData().get("tipo"));
        task.setSolicitante(remoteMessage.getData().get("solicitante"));
        task.setTrabajo_solicitado(remoteMessage.getData().get("trabajo_solicitado"));
        task.setPiso(remoteMessage.getData().get("piso"));
        task.setSubarea(remoteMessage.getData().get("subarea"));
        task.setId(remoteMessage.getData().get("id"));
        if (remoteMessage.getData().size() > 0) {
            topics=remoteMessage.getFrom();
            estado=remoteMessage.getData().get("estado");
            if(topics.equals("/topics/usuario_prueba")) {
                if (estado.equals("No iniciado")) {

                    administrador_notificaciones adm = new administrador_notificaciones(NotificationService.this);
                    Notification.Builder nb = adm.createNotification("", "", true, task);
                    adm.getManager().notify(task.getId().hashCode(), nb.build());
                }
            }
            if(topics.equals("/topics/administrador_prueba"))
            {
                if(!estado.equals("No iniciado")) {

                    administrador_notificaciones adm1 = new administrador_notificaciones(NotificationService.this);
                    Notification.Builder nb1 = adm1.createNotification("", "", true, task);
                    adm1.getManager().notify(task.getId().hashCode(), nb1.build());
                }
            }

        }
    }
}
