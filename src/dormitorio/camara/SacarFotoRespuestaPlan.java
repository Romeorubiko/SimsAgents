package dormitorio.camara;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.SacarFoto;
import ontologia.conceptos.Foto;
import ontologia.conceptos.habilidades.Fotografia;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.FotoRealizada;
import ontologia.predicados.CamaraEstropeadaSacarFoto;

public class SacarFotoRespuestaPlan extends Plan {

    public SacarFotoRespuestaPlan() {
    }

    @Override
    public void body() {
        IMessageEvent peticion = (IMessageEvent) getBeliefbase().getBelief("mensaje_camara").getFact();
        getBeliefbase().getBelief("tiempo_fin_foto").setFact(0);
        getBeliefbase().getBelief("ocupado_camara").setFact(Boolean.FALSE);

        SacarFoto content = (SacarFoto) peticion.getContent();
        Foto foto = content.getFoto();
        Diversion diversion = content.getDiversion();
        Energia energia = content.getEnergia();
        Fotografia fotografia = content.getFotografia();

        IMessageEvent respuesta = createMessageEvent("foto_realizada");
        respuesta.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());

        if (foto.getTipo() == Foto.TiposFoto.PANORAMICA && fotografia.getNivel() >= 4) {
            energia.setGrado(content.getEnergia().getGrado() - Necesidad.NC_POCO);
            diversion.setGrado(diversion.getGrado() + Necesidad.NC_NORMAL);
            fotografia.setExperiencia((fotografia.getExperiencia() + Habilidad.HB_NORMAL));
        }

        if (foto.getSize() == Foto.FotoSize.LARGE && fotografia.getNivel() >= 4) {
            energia.setGrado(content.getEnergia().getGrado() - Necesidad.NC_POCO);
            diversion.setGrado(diversion.getGrado() + Necesidad.NC_NORMAL);
            fotografia.setExperiencia((fotografia.getExperiencia() + Habilidad.HB_NORMAL));
        }

        if ((foto.getFiltro() == Foto.Filtro.SEPIA || foto.getFiltro() == Foto.Filtro.VIGNETTE) && fotografia.getNivel() >= 3) {
            energia.setGrado(content.getEnergia().getGrado() - Necesidad.NC_POCO);
            diversion.setGrado(diversion.getGrado() + Necesidad.NC_NORMAL);
            fotografia.setExperiencia((fotografia.getExperiencia() + Habilidad.HB_NORMAL));
        }

        FotoRealizada fotoRealizada = new FotoRealizada(energia, diversion, fotografia);
        respuesta.setContent(fotoRealizada);
        sendMessage(respuesta);
    }
}
