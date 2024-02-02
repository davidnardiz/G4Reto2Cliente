/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Gonzalo
 */
@XmlRootElement(name = "tiendaEvento")
public class TiendaEvento implements Serializable {

    //Variables
    private static final long serialVersionUID = 1L;
    private SimpleObjectProperty<TiendaEventoId> idTiendaEvento;
    private SimpleObjectProperty<Tienda> tienda;
    private SimpleObjectProperty<Evento> evento;
    private SimpleObjectProperty<Date> fechaInscripcion;

    //Constructores.
    public TiendaEvento(TiendaEventoId idTiendaEvento, Tienda tienda, Evento evento, Date fechaCreacion) {
        this.idTiendaEvento = new SimpleObjectProperty<>(idTiendaEvento);
        this.tienda = new SimpleObjectProperty<>(tienda);
        this.evento = new SimpleObjectProperty<>(evento);
        this.fechaInscripcion = new SimpleObjectProperty<>(fechaCreacion);

    }

    public TiendaEvento() {
        this.idTiendaEvento = new SimpleObjectProperty<>();
        this.tienda = new SimpleObjectProperty<>();
        this.evento = new SimpleObjectProperty<>();
        this.fechaInscripcion = new SimpleObjectProperty<>();
    }

    //Getters y setters.
    public TiendaEventoId getIdTiendaEvento() {
        return this.idTiendaEvento.get();
    }

    public void setIdTiendaEvento(TiendaEventoId idTiendaEvento) {
        this.idTiendaEvento.set(idTiendaEvento);
    }

    public Tienda getTienda() {
        return this.tienda.get();
    }

    public void setTienda(Tienda tienda) {
        this.tienda.set(tienda);
    }

    public Evento getEvento() {
        return this.evento.get();
    }

    public void setEvento(Evento evento) {
        this.evento.set(evento);
    }

    public Date getFechaInscripcion() {
        return this.fechaInscripcion.get();
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion.set(fechaInscripcion);
    }

    //To string para mostrar la informacion
    @Override
    public String toString() {
        return "TiendaEvento{" + "idTiendaEvento=" + idTiendaEvento +/* ", tienda=" + tienda +*/ ", evento=" + evento + ", fechaInscripcion=" + fechaInscripcion + '}';
    }

}
