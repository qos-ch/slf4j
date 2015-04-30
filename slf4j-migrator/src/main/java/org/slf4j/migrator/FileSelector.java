/**
 * Copyright (c) 2004-2011 QOS.ch
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.slf4j.migrator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.migrator.internal.ProgressListener;

public class FileSelector {

    private List<File> javaFileList = new ArrayList<File>();

    ProgressListener pl;

    FileSelector(ProgressListener pl) {
        this.pl = pl;
    }

    public List<File> selectJavaFilesInFolder(File folder) {
        if (folder.isDirectory()) {
            selectFiles(folder);
            return javaFileList;
        } else {
            throw new IllegalArgumentException("[" + folder + "] is not a directory");
        }
    }

    private void selectFiles(File file) {
        if (file.isDirectory()) {
            pl.onDirectory(file);
            File[] files = file.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    selectFiles(files[i]);
                }
            }
        } else {
            if (file.getName().endsWith(".java")) {
                pl.onFileAddition(file);
                javaFileList.add(file);
            }

        }
    }
}
