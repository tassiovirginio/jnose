/*******************************************************************************
 * Copyright (c) 2009, 2019 Mountainminds GmbH & Co. KG and Contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Marc R. Hoffmann - initial API and implementation
 *    
 *******************************************************************************/
package br.ufba.jnose.base.cobertura;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;

import org.jacoco.core.data.ExecutionData;
import org.jacoco.core.data.ExecutionDataReader;
import org.jacoco.core.data.IExecutionDataVisitor;
import org.jacoco.core.data.ISessionInfoVisitor;
import org.jacoco.core.data.SessionInfo;

public final class ExecDump {

	private final PrintStream out;

	public ExecDump(final PrintStream out) {
		this.out = out;
	}

	public void execute(final String[] args) throws IOException {
		for (final String file : args) {
			dump(file);
		}
	}

	private void dump(final String file) throws IOException {
		out.printf("exec file: %s%n", file);
		out.println("CLASS ID         HITS/PROBES   CLASS NAME");

		final FileInputStream in = new FileInputStream(file);
		final ExecutionDataReader reader = new ExecutionDataReader(in);
		reader.setSessionInfoVisitor(new ISessionInfoVisitor() {
			public void visitSessionInfo(final SessionInfo info) {
				out.printf("Session \"%s\": %s - %s%n", info.getId(), new Date(
						info.getStartTimeStamp()),
						new Date(info.getDumpTimeStamp()));
			}
		});
		reader.setExecutionDataVisitor(new IExecutionDataVisitor() {
			public void visitClassExecution(final ExecutionData data) {
				out.printf("%016x  %3d of %3d   %s%n",
						Long.valueOf(data.getId()),
						Integer.valueOf(getHitCount(data.getProbes())),
						Integer.valueOf(data.getProbes().length),
						data.getName());
			}
		});
		reader.read();
		in.close();
		out.println();
	}

	private int getHitCount(final boolean[] data) {
		int count = 0;
		for (final boolean hit : data) {
			if (hit) {
				count++;
			}
		}
		return count;
	}

	public static void main(final String[] args) throws IOException {
		new ExecDump(System.out).execute(args);
	}
}
