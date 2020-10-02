package com.pfizer.sacchonapi.resource.MediData;

import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.model.MediData;
import com.pfizer.sacchonapi.repository.MediDataRepository;
import com.pfizer.sacchonapi.repository.util.JpaUtil;
import com.pfizer.sacchonapi.representation.MediDataRepresentation;
import com.pfizer.sacchonapi.security.ResourceUtils;
import com.pfizer.sacchonapi.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MediDataConsultationResourceImpl extends ServerResource implements MediDataConsultationResource{
    public static final Logger LOGGER = Engine.getLogger(MediDataConsultationResourceImpl.class);
    private MediDataRepository mediDataRepository;

    private long cid;
    private EntityManager em;

    @Override
    protected void doRelease() {
        em.close();
    }

    @Override
    protected void doInit() {
        LOGGER.info("Initialising consultation resource starts");
        try {
            em = JpaUtil.getEntityManager();
            mediDataRepository = new MediDataRepository(em);
            cid = Long.parseLong(getAttribute("cid"));
        }catch (Exception e) {
            cid = -1;
            LOGGER.info(e.getMessage());
        }

    }

    @Override
    public List<MediDataRepresentation> getMediData() throws NotFoundException {
        LOGGER.finer("Select medical data of a consultation.");

        ResourceUtils.checkRoles(this, Shield.patient, Shield.doctor);
        try {
            List<MediData> mediData;
            List<MediDataRepresentation> result = new ArrayList<>();
            if(cid != -1){
                mediData = mediDataRepository.findConsultationMedi(cid);
            }else{
               mediData = null;
            }
            mediData.forEach(m -> result.add(new MediDataRepresentation(m)));
            return result;
        } catch (Exception e) {
            System.out.println(cid);
            throw new NotFoundException("Medical data not found");
        }
    }
}
