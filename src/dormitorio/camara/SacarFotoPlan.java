package dormitorio.camara;

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

public class SacarFotoPlan extends Plan {
    public SacarFotoPlan() {
    }

    @Override
    public void body() {
        RMessageEvent peticion = ((RMessageEvent) getInitialEvent());
        SacarFoto content = (SacarFoto) peticion.getContent();

        Foto foto = content.getFoto();
        Energia energia = content.getEnergia();
        Diversion diversion = content.getDiversion();
        Fotografia fotografia = content.getFotografia();

        // Disminuye en uno la cantidad de usos restantes hasta el deterioro de la cámara.
        Integer obsolescencia = (Integer) getBeliefbase().getBelief("obsolescencia").getFact() - 1;

        if (obsolescencia <= 0) {
            IMessageEvent respuesta = createMessageEvent("camara_estropeada_sacar_foto");
            CamaraEstropeadaSacarFoto camaraEstropeadaSacarFoto = new CamaraEstropeadaSacarFoto(energia, diversion, fotografia);
            respuesta.setContent(camaraEstropeadaSacarFoto);
            sendMessage(respuesta);
        } else {
            getBeliefbase().getBelief("obsolescencia").setFact(obsolescencia);

            if (foto.getTipo() == Foto.TiposFoto.PANORAMICA && fotografia.getNivel() >= 4) {
                energia.setGrado(content.getEnergia().getGrado() - Necesidad.NC_POCO);
                content.setEnergia(energia);

                diversion.setGrado(diversion.getGrado() + Necesidad.NC_NORMAL);
                content.setDiversion(diversion);

                fotografia.setExperiencia((fotografia.getExperiencia() + Habilidad.HB_NORMAL));
                content.setFotografia(fotografia);
            }
            if (foto.getSize() == Foto.FotoSize.LARGE && fotografia.getNivel() >= 4) {
                energia.setGrado(content.getEnergia().getGrado() - Necesidad.NC_POCO);
                content.setEnergia(energia);

                diversion.setGrado(diversion.getGrado() + Necesidad.NC_NORMAL);
                content.setDiversion(diversion);

                fotografia.setExperiencia((fotografia.getExperiencia() + Habilidad.HB_NORMAL));
                content.setFotografia(fotografia);
            }
            if ((foto.getFiltro() == Foto.Filtro.SEPIA || foto.getFiltro() == Foto.Filtro.VIGNETTE) && fotografia.getNivel() >= 3) {
                energia.setGrado(content.getEnergia().getGrado() - Necesidad.NC_POCO);
                content.setEnergia(energia);

                diversion.setGrado(diversion.getGrado() + Necesidad.NC_NORMAL);
                content.setDiversion(diversion);

                fotografia.setExperiencia((fotografia.getExperiencia() + Habilidad.HB_NORMAL));
                content.setFotografia(fotografia);
            }

            try {
                wait(Accion.TIEMPO_MEDIO);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            IMessageEvent respuesta = createMessageEvent("foto_realizada");
            FotoRealizada fotoRealizada = new FotoRealizada(energia, diversion, fotografia);
            respuesta.setContent(fotoRealizada);
            sendMessage(respuesta);

        }
    }
}