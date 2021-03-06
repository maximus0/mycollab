/**
 * This file is part of mycollab-servlet.
 *
 * mycollab-servlet is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-servlet is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-servlet.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.esofthead.mycollab.module.file.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esofthead.mycollab.core.utils.MimeTypesUtil;
import com.esofthead.mycollab.module.ecm.service.ResourceService;
import com.esofthead.mycollab.servlet.GenericServletRequestHandler;

/**
 * 
 * @author MyCollab Ltd.
 * @since 4.4.0
 *
 */
@Component("resourceGetHandler")
public class ResourceGetHandler extends GenericServletRequestHandler {

	private static final Logger LOG = LoggerFactory
			.getLogger(ResourceGetHandler.class);

	@Autowired
	private ResourceService resourceService;

	@Override
	protected void onHandleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		InputStream inputStream = resourceService.getContentStream(path);

		if (inputStream != null) {
			LOG.debug("Get resource {} successfully ", path);
			response.setHeader("Content-Type",
					MimeTypesUtil.detectMimeType(path));
			response.setHeader("Content-Length",
					String.valueOf(inputStream.available()));

			BufferedInputStream input = null;
			BufferedOutputStream output = null;

			try {
				input = new BufferedInputStream(inputStream);
				output = new BufferedOutputStream(response.getOutputStream());
				byte[] buffer = new byte[8192];
				int length = 0;
				while ((length = input.read(buffer)) > 0) {
					output.write(buffer, 0, length);
				}
			} finally {
				if (output != null)
					try {
						output.close();
					} catch (IOException logOrIgnore) {
					}
				if (input != null)
					try {
						input.close();
					} catch (IOException logOrIgnore) {
					}
			}
		} else {
			LOG.error("Can not find resource has path {}", path);
		}

	}
}
