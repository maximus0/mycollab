/**
 * This file is part of mycollab-core.
 *
 * mycollab-core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-core.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.esofthead.mycollab.html;

import com.hp.gagawa.java.Node;
import com.hp.gagawa.java.elements.Div;
import com.hp.gagawa.java.elements.Text;

/**
 * 
 * @author MyCollab Ltd.
 * @since 4.3.1
 *
 */
public class DivLessFormatter extends Div {
	public static Text EMPTY_SPACE = new Text(" ");

	@Override
	public String write() {
		StringBuffer b = new StringBuffer();
		if ((this.children != null) && (this.children.size() > 0)) {
			for (Node child : this.children) {
				b.append(child.write());
			}
		}
		return b.toString();
	}

}
