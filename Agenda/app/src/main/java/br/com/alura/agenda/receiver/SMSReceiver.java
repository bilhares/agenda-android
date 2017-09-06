package br.com.alura.agenda.receiver;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsMessage;
import android.widget.Toast;

import br.com.alura.agenda.R;
import br.com.alura.agenda.dao.AlunoDAO;

/**
 * Created by USER on 06/09/2017.
 */

public class SMSReceiver extends BroadcastReceiver{
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {

        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");
        byte[] pdu = (byte[]) pdus[0];
        String formato = (String) intent.getSerializableExtra("format");

        SmsMessage sms = SmsMessage.createFromPdu(pdu, formato);

        String telefone = sms.getDisplayOriginatingAddress();
        String msg = sms.getMessageBody();

        AlunoDAO dao = new AlunoDAO(context);
        if(dao.ehALuno(telefone)){
            Toast.makeText(context, "Mensagem recebida: "+msg, Toast.LENGTH_SHORT).show();

//            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
//            alertDialog.setTitle("Alert");
//            alertDialog.setMessage("Alert message to be shown");
//            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//            alertDialog.show();

            MediaPlayer m  = MediaPlayer.create(context, R.raw.msg);
            m.start();
        }
        dao.close();
    }
}
