package com.postgres.demopg.whatsapp.controller;

import com.postgres.demopg.whatsapp.dto.CallMockDTO;
import com.postgres.demopg.whatsapp.dto.ChatMockDTO;
import com.postgres.demopg.whatsapp.dto.MessageMockDTO;
import com.postgres.demopg.whatsapp.dto.StatusMockDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/whatsapp")
public class WhatsAppMockController {

    @GetMapping("/chats")
    public ArrayList<ChatMockDTO> getChats() {
        ArrayList<ChatMockDTO> chats = new ArrayList<>();

        ArrayList<MessageMockDTO> mensajesCarlos = new ArrayList<>();
        mensajesCarlos.add(new MessageMockDTO("Qué onda bro, ya terminaste la app?", "10:35 p. m.", false, true));
        mensajesCarlos.add(new MessageMockDTO("Sí, ya quedó la parte conectada a la API", "10:38 p. m.", true, true));
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

        return chats;
    }

    @GetMapping("/chats/{id}/messages")
    public ArrayList<MessageMockDTO> getMessagesByChatId(@PathVariable int id) {
        ArrayList<ChatMockDTO> chats = getChats();

        for (ChatMockDTO chat : chats) {
            if (chat.getId() == id) {
                return new ArrayList<>(chat.getMessages());
            }
        }

        return new ArrayList<>();
    }

    @GetMapping("/statuses")
    public ArrayList<StatusMockDTO> getStatuses() {
        ArrayList<StatusMockDTO> statuses = new ArrayList<>();

        statuses.add(new StatusMockDTO("Carlos", "C", "Hace 12 minutos", false));
        statuses.add(new StatusMockDTO("Victoria", "V", "Hoy, 8:20 p. m.", false));
        statuses.add(new StatusMockDTO("Equipo de Proyecto", "E", "Hoy, 7:45 p. m.", true));
        statuses.add(new StatusMockDTO("Mamá", "M", "Hoy, 6:10 p. m.", true));

        return statuses;
    }

    @GetMapping("/calls")
    public ArrayList<CallMockDTO> getCalls() {
        ArrayList<CallMockDTO> calls = new ArrayList<>();

        calls.add(new CallMockDTO("Carlos", "C", "Hoy, 10:10 p. m.", "incoming", false));
        calls.add(new CallMockDTO("Victoria", "V", "Hoy, 8:31 p. m.", "missed", true));
        calls.add(new CallMockDTO("Mamá", "M", "Ayer, 9:22 p. m.", "outgoing", false));
        calls.add(new CallMockDTO("Equipo de Proyecto", "E", "Ayer, 6:14 p. m.", "incoming", true));

        return calls;
    }
}