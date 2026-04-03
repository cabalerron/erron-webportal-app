/*
 * HM001
 *
 * v 00.001 - 10/21/2024
 *
 * PIC: emonteverde
 */

package ph.com.project01.webportal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ph.com.project01.webportal.model.News;

@Service
public interface HM001Service {
    List<News> getArcticleTitle();
    String getScreenTitle();

}