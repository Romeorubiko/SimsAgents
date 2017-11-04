package dormitorio.camara;

import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.SacarFotoRetrato;
import ontologia.conceptos.Foto;
import ontologia.conceptos.Retrato;
import ontologia.conceptos.habilidades.Fotografia;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.InteraccionSocial;
import ontologia.conceptos.necesidades.Necesidad;

public class SacarFotoRetratoPlan extends Plan {
    public SacarFotoRetratoPlan() {
    }

    @Override
    public void body() {
        RMessageEvent peticion = ((RMessageEvent) getInitialEvent());
        SacarFotoRetrato content = (SacarFotoRetrato) peticion.getContent();

        Retrato retrato = content.getRetrato();

        Diversion diversion = content.getDiversion();
        Energia energia = content.getEnergia();
        InteraccionSocial interaccionSocial = content.getInteraccionSocial();
        Fotografia fotografia = content.getFotografia();

        if(retrato.getFotoSize()== Foto.FotoSize.LARGE && fotografia.getNivel()>=4){

            diversion.setGrado(content.getDiversion().getGrado() + Necesidad.NC_NORMAL);
            content.setDiversion(diversion);

            energia.setGrado(content.getEnergia().getGrado() - Necesidad.NC_POCO);
            content.setEnergia(energia);

            interaccionSocial.setGrado(interaccionSocial.getGrado()+ Habilidad.HB_NORMAL);
            content.setInteraccionSocial(interaccionSocial);

            fotografia.setExperiencia((fotografia.getExperiencia()+ Habilidad.HB_NORMAL));
            content.setFotografia(fotografia);
        }
        if((retrato.getFiltro()==Foto.Filtro.SEPIA || retrato.getFiltro()==Foto.Filtro.VIGNETTE) && fotografia.getNivel()>=3){

            diversion.setGrado(content.getDiversion().getGrado() + Necesidad.NC_NORMAL);
            content.setDiversion(diversion);

            energia.setGrado(content.getEnergia().getGrado() - Necesidad.NC_POCO);
            content.setEnergia(energia);

            interaccionSocial.setGrado(interaccionSocial.getGrado()+ Habilidad.HB_NORMAL);
            content.setInteraccionSocial(interaccionSocial);

            fotografia.setExperiencia((fotografia.getExperiencia()+ Habilidad.HB_NORMAL));
            content.setFotografia(fotografia);
        }

        try {
            wait(Accion.TIEMPO_MEDIO);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        IMessageEvent respuesta = createMessageEvent("foto_retrato_realizada");
        respuesta.setContent(content);
        sendMessage(respuesta);

    }
}
