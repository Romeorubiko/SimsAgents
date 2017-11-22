package dormitorio.camara;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;

import ontologia.acciones.SacarFotoRetrato;
import ontologia.conceptos.Foto;
import ontologia.conceptos.Retrato;
import ontologia.conceptos.habilidades.Fotografia;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.InteraccionSocial;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.FotoRealizada;

public class SacarFotoRetratoRespuestaPlan extends Plan {

    public SacarFotoRetratoRespuestaPlan() {
    }

    @Override
    public void body() {
        IMessageEvent peticion = (IMessageEvent) getBeliefbase().getBelief("mensaje_camara").getFact();
        getBeliefbase().getBelief("tiempo_fin_foto").setFact(0);
        getBeliefbase().getBelief("ocupado_camara").setFact(Boolean.FALSE);
        getGoalbase().getGoal("sacar_foto_tiempo_superado").drop();

        SacarFotoRetrato content = (SacarFotoRetrato) peticion.getContent();
        Retrato retrato = content.getRetrato();
        Diversion diversion = content.getDiversion();
        Energia energia = content.getEnergia();
        InteraccionSocial interaccionSocial = content.getInteraccionSocial();
        Fotografia fotografia = content.getFotografia();

        IMessageEvent respuesta = createMessageEvent("foto_realizada");
        respuesta.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());

        if (retrato.getFotoSize() == Foto.FotoSize.LARGE && fotografia.getNivel() >= 4) {
            diversion.setGrado(content.getDiversion().getGrado() + Necesidad.NC_NORMAL);
            energia.setGrado(content.getEnergia().getGrado() - Necesidad.NC_POCO);
            interaccionSocial.setGrado(interaccionSocial.getGrado() + Habilidad.HB_NORMAL);
            fotografia.setExperiencia((fotografia.getExperiencia() + Habilidad.HB_NORMAL));
        }
        if ((retrato.getFiltro() == Foto.Filtro.SEPIA || retrato.getFiltro() == Foto.Filtro.VIGNETTE) && fotografia.getNivel() >= 3) {
            diversion.setGrado(content.getDiversion().getGrado() + Necesidad.NC_NORMAL);
            energia.setGrado(content.getEnergia().getGrado() - Necesidad.NC_POCO);
            interaccionSocial.setGrado(interaccionSocial.getGrado() + Habilidad.HB_NORMAL);
            fotografia.setExperiencia((fotografia.getExperiencia() + Habilidad.HB_NORMAL));
        }

        //CREO UN SEGUNDO CONSTRUCTOR EN FOTO REALIZADA CON LOS ATRIBUTOS DE FOTO RETRATO
        FotoRealizada fotoRealizada = new FotoRealizada(diversion, energia, interaccionSocial, fotografia);
        respuesta.setContent(fotoRealizada);
        sendMessage(respuesta);


    }
}
