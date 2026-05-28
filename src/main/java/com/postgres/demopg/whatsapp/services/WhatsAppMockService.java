package com.postgres.demopg.whatsapp.service;

import com.postgres.demopg.whatsapp.dto.CallMockDTO;
import com.postgres.demopg.whatsapp.dto.ChatMockDTO;
import com.postgres.demopg.whatsapp.dto.MessageMockDTO;
import com.postgres.demopg.whatsapp.dto.StatusMockDTO;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
public class WhatsAppMockService {

    private final ArrayList<ChatMockDTO> chats = new ArrayList<>();
    private final ArrayList<StatusMockDTO> statuses = new ArrayList<>();
    private final ArrayList<CallMockDTO> calls = new ArrayList<>();

    public WhatsAppMockService() {
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        if (!chats.isEmpty()) {
            return;
        }

        ArrayList<MessageMockDTO> mensajesCarlos = new ArrayList<>();
        mensajesCarlos.add(new MessageMockDTO("Qué onda bro, ya terminaste la app?", "10:35 p. m.", false, true));
        mensajesCarlos.add(new MessageMockDTO("Sí, ya quedó conectada a la API", "10:38 p. m.", true, true));
        mensajesCarlos.add(new MessageMockDTO("Va bro, mañana lo checamos", "10:42 p. m.", false, true));

        chats.add(new ChatMockDTO(
                1,
                "Carlos",
                "C",
                "Va bro, mañana lo checamos",
                "10:42 p. m.",
                2,
                true,
                false,
                mensajesCarlos
        ));

        ArrayList<MessageMockDTO> mensajesMama = new ArrayList<>();
        mensajesMama.add(new MessageMockDTO("Ya cenaste?", "9:10 p. m.", false, true));
        mensajesMama.add(new MessageMockDTO("Ahorita voy", "9:15 p. m.", true, true));
        mensajesMama.add(new MessageMockDTO("No llegues tarde", "9:18 p. m.", false, true));

        chats.add(new ChatMockDTO(
                2,
                "Mamá",
                "M",
                "No llegues tarde",
                "9:18 p. m.",
                1,
                false,
                false,
                mensajesMama
        ));

        ArrayList<MessageMockDTO> mensajesEquipo = new ArrayList<>();
        mensajesEquipo.add(new MessageMockDTO("Ya hicieron la parte de pantallas?", "8:40 p. m.", false, true));
        mensajesEquipo.add(new MessageMockDTO("Yo ya tengo chats y llamadas", "8:44 p. m.", true, true));
        mensajesEquipo.add(new MessageMockDTO("Manden captura de cómo les quedó", "8:56 p. m.", false, true));

        chats.add(new ChatMockDTO(
                3,
                "Equipo de Proyecto",
                "E",
                "Manden captura de cómo les quedó",
                "8:56 p. m.",
                5,
                false,
                true,
                mensajesEquipo
        ));

        ArrayList<MessageMockDTO> mensajesVictoria = new ArrayList<>();
        mensajesVictoria.add(new MessageMockDTO("Hola, cómo estás?", "7:20 p. m.", true, true));
        mensajesVictoria.add(new MessageMockDTO("Bien y tú?", "7:24 p. m.", false, true));
        mensajesVictoria.add(new MessageMockDTO("Jajaja está bien", "7:30 p. m.", false, true));

        chats.add(new ChatMockDTO(
                4,
                "Victoria",
                "V",
                "Jajaja está bien",
                "7:30 p. m.",
                0,
                false,
                false,
                mensajesVictoria
        ));

        ArrayList<MessageMockDTO> mensajesProfe = new ArrayList<>();
        mensajesProfe.add(new MessageMockDTO("Recuerden entregar app-hardcode", "5:58 p. m.", false, true));
        mensajesProfe.add(new MessageMockDTO("Sí profe, mañana la presentamos", "6:01 p. m.", true, true));
        mensajesProfe.add(new MessageMockDTO("Ahora quiero ver el API en Render", "6:05 p. m.", false, true));

        chats.add(new ChatMockDTO(
                5,
                "Profe Base de Datos",
                "P",
                "Ahora quiero ver el API en Render",
                "6:05 p. m.",
                1,
                false,
                false,
                mensajesProfe
        ));

        statuses.add(new StatusMockDTO("Carlos", "C", "Hace 12 minutos", false));
        statuses.add(new StatusMockDTO("Victoria", "V", "Hoy, 8:20 p. m.", false));
        statuses.add(new StatusMockDTO("Equipo de Proyecto", "E", "Hoy, 7:45 p. m.", true));
        statuses.add(new StatusMockDTO("Mamá", "M", "Hoy, 6:10 p. m.", true));

        calls.add(new CallMockDTO("Carlos", "C", "Hoy, 10:10 p. m.", "incoming", false));
        calls.add(new CallMockDTO("Victoria", "V", "Hoy, 8:31 p. m.", "missed", true));
        calls.add(new CallMockDTO("Mamá", "M", "Ayer, 9:22 p. m.", "outgoing", false));
        calls.add(new CallMockDTO("Equipo de Proyecto", "E", "Ayer, 6:14 p. m.", "incoming", true));
    }

    public ArrayList<ChatMockDTO> getChats() {
        return chats;
    }

    public ChatMockDTO getChatById(int id) {
        for (ChatMockDTO chat : chats) {
            if (chat.getId() == id) {
                return chat;
            }
        }

        return null;
    }

    public ArrayList<MessageMockDTO> getMessagesByChatId(int chatId) {
        ChatMockDTO chat = getChatById(chatId);

        if (chat == null) {
            return new ArrayList<>();
        }

        return chat.getMessages();
    }

    public MessageMockDTO sendMessage(int chatId, String text) {
        ChatMockDTO chat = getChatById(chatId);

        if (chat == null) {
            return null;
        }

        MessageMockDTO newMessage = new MessageMockDTO(
                text,
                getCurrentTime(),
                true,
                true
        );

        chat.getMessages().add(newMessage);
        chat.setLastMessage(text);
        chat.setTime(newMessage.getTime());
        chat.setUnreadMessages(0);

        System.out.println("Mensaje guardado en chat " + chatId + ": " + text);
        System.out.println("Total mensajes en chat " + chatId + ": " + chat.getMessages().size());

        return newMessage;
    }

    public boolean clearMessages(int chatId) {
        ChatMockDTO chat = getChatById(chatId);

        if (chat == null) {
            return false;
        }

        chat.getMessages().clear();
        chat.setLastMessage("Chat vacío");
        chat.setTime(getCurrentTime());
        chat.setUnreadMessages(0);

        return true;
    }

    public ArrayList<StatusMockDTO> getStatuses() {
        return statuses;
    }

    public ArrayList<CallMockDTO> getCalls() {
        return calls;
    }

    private String getCurrentTime() {
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm");

        String hourMinute = now.format(formatter);
        String period = now.getHour() < 12 ? "a. m." : "p. m.";

        return hourMinute + " " + period;
    }
}