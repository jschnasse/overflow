/*******************************************************************************
 * Copyright 2017 Jan Schnasse
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/

package stack49410929;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

public class SubtractCollection {

	@Test
	public void subtract1() {
		List<Integer> a = new ArrayList<>(Arrays.asList(1, 2, 2));
		List<Integer> b = Arrays.asList(2, 3, 4);
		b.forEach(i -> a.remove(i));
		System.out.println(a);
	}

	@Test
	public void subtract2() {
		List<Integer> a = new ArrayList<>(Arrays.asList(1, 2, 2));
		List<Integer> b = Arrays.asList(2, 3, 4);
		Collection<Integer> result = CollectionUtils.subtract(a, b);
		System.out.println(result);
	}

	@Test
	public void subtract3() {
		ArrayList<Integer> a = new ArrayList<Integer>();
		a.addAll(Arrays.asList(1, 2, 2, 3, 3));
		List<Integer> b = Arrays.asList(1, 2, 4, 6, 5);
		a.removeAll(b);
		System.out.println(a);
	}

	@Test
	public void subtract4() {
		List<Integer> list1 = Arrays.asList(1, 2, 2, 3, 3);
		List<Integer> list2 = Arrays.asList(1, 2, 4, 6, 5);
		List<Integer> diff = list1.stream().filter(e -> !list2.contains(e)).collect(Collectors.toList());
		System.out.println(diff);
	}
}