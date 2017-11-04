package dormitorio.camara;

import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.SacarFoto;
import ontologia.conceptos.Foto;
import ontologia.conceptos.habilidades.Fotografia;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;

public class SacarFotoPlan extends Plan {
    public SacarFotoPlan() {
    }

    @Override
    public void body() {
        RMessageEvent peticion = ((RMessageEvent) getInitialEvent());
        SacarFoto content = (SacarFoto) peticion.getContent();

        Foto foto = content.getFoto();
        Energia energia = content.getEnergia();
        Fotografia fotografia = content.getFotografia();

        if(foto.getTipo()==Foto.TiposFoto.PANORAMICA && fotografia.getNivel()>=4){
            energia.setGrado(content.getEnergia().getGrado() - Necesidad.NC_POCO);
            content.setEnergia(energia);

            fotografia.setExperiencia((fotografia.getExperiencia()+ Habilidad.HB_NORMAL));
            content.setFotografia(fotografia);
        }
        if(foto.getSize()==Foto.FotoSize.LARGE && fotografia.getNivel()>=4){
            energia.setGrado(content.getEnergia().getGrado() - Necesidad.NC_POCO);
            content.setEnergia(energia);

            fotografia.setExperiencia((fotografia.getExperiencia()+ Habilidad.HB_NORMAL));
            content.setFotografia(fotografia);
        }
        if((foto.getFiltro()==Foto.Filtro.SEPIA || foto.getFiltro()==Foto.Filtro.VIGNETTE) && fotografia.getNivel()>=3){
            energia.setGrado(content.getEnergia().getGrado() - Necesidad.NC_POCO);
            content.setEnergia(energia);

            fotografia.setExperiencia((fotografia.getExperiencia()+ Habilidad.HB_NORMAL));
            content.setFotografia(fotografia);
        }

        try {
            wait(Accion.TIEMPO_MEDIO);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        IMessageEvent respuesta = createMessageEvent("foto_realizada");
        respuesta.setContent(content);
        sendMessage(respuesta);

    }
}
