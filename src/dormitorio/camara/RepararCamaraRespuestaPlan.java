package dormitorio.camara;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import ontologia.acciones.RepararCamara;
import ontologia.acciones.SacarFoto;
import ontologia.conceptos.Foto;
import ontologia.conceptos.habilidades.Fotografia;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.habilidades.Mecanica;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.CamaraReparada;
import ontologia.predicados.FotoRealizada;

public class RepararCamaraRespuestaPlan extends Plan {

    public RepararCamaraRespuestaPlan() {
    }

    @Override
    public void body() {
        IMessageEvent peticion = (IMessageEvent) getBeliefbase().getBelief("mensaje_camara").getFact();
        getBeliefbase().getBelief("tiempo_fin_foto").setFact(0);
        getBeliefbase().getBelief("ocupado_camara").setFact(Boolean.FALSE);

        RepararCamara content = (RepararCamara) peticion.getContent();
        Energia energia = content.getEnergia();
        Higiene higiene = content.getHigiene();
        Mecanica mecanica = content.getMecanica();

        energia.setGrado(energia.getGrado() - Necesidad.NC_POCO);
        higiene.setGrado(higiene.getGrado() - Necesidad.NC_POCO);
        mecanica.setExperiencia(mecanica.getExperiencia() + Habilidad.HB_NORMAL);

        IMessageEvent respuesta = createMessageEvent("camara_reparada");
        respuesta.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());

        CamaraReparada camaraReparada = new CamaraReparada(energia, higiene, mecanica);
        respuesta.setContent(camaraReparada);
        sendMessage(respuesta);
    }
}
