# MySpringRS

## Daniel Hintze trial work

_Das Projekt verwendet LOMBOK_

### Die API wird bereitgestellt in [ShoppingRestController.java](src%2Fmain%2Fjava%2Fcom%2Fhintze%2Fmyspringrs%2Fcontroller%2FShoppingRestController.java)

#### mit den folgenden Endpoints:

| Path                                    | Method |
|:----------------------------------------|:------:|
| /coolschrank/desiredquantity            |  POST  |
| /coolschrank/fridge                     |  POST  |
| /coolschrank/fridge/{fridgeId}          |  GET   |
| /coolschrank/missingproducts/{fridgeId} |  GET   |
| /coolschrank/missingproducts/{fridgeId} |  POST  | 

#### Es werden 2 Domain-Objekte verwendet:

**[Fridge.java](src%2Fmain%2Fjava%2Fcom%2Fhintze%2Fmyspringrs%2Fmodel%2FFridge.java)**

```JSON
{
  "id": "xyz",
  "inventory": []
}
```     

**[ProductItem.java](src%2Fmain%2Fjava%2Fcom%2Fhintze%2Fmyspringrs%2Fmodel%2FProductItem.java)**

```JSON
{
  "id": 1,
  "name": "Apfelsaft",
  "actual": 1.0,
  "target": 10.0
}
``` 

_Die Java Doc orientiert sich bei Nummern und Beschreibung an der PDF_

### Die gegebene REST Api wird in [ShoppingRestConsumer.java](src%2Fmain%2Fjava%2Fcom%2Fhintze%2Fmyspringrs%2Fcontroller%2FShoppingRestConsumer.java) konsumiert

_Die Java Doc orientiert sich an der REST Api Dokumentation_

Note: _Die Basic Auth habe ich wieder entfernt, da die gegebene API kein Auth verlangt_

#### 5. Optional:

```
Gedankenexperiment, ohne Umsetzung/Implementierung: Wie würde man den
Service cloudfähig machen? (Stichworte Skalierbarkeit, Logging, Monitoring, etc.)
```

* den Service stateless machen
* Den Service in Docker-Container verpacken und mit Kubernetis betreiben
* Loggen mit Graylog
* Monitoring mit Grafana
* In Prometheus einbinden