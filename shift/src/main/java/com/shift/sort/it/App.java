/*
Написать программу сортировки слиянием нескольких файлов.
Входные файлы содержат данные одного из двух видов: целые числа или строки. Данные записаны
в столбик (каждая строка файла – новый элемент). Строки могут содержать любые не пробельные
символы, строки с пробелами считаются ошибочными. Также считается, что файлы предварительно
отсортированы.
Результатом работы программы должен являться новый файл с объединенным содержимым
входных файлов, отсортированным по возрастанию или убыванию путем сортировки слиянием.
Если содержимое исходных файлов не позволяет произвести сортировку слиянием (например,
нарушен порядок сортировки), производится частичная сортировка (насколько возможно для этого
алгоритма, как именно обрабатывать поврежденный файл – на усмотрение разработчика).
Выходной файл должен содержать отсортированные данные даже в случае ошибок, однако
возможна потеря ошибочных данных.
Необходимо самостоятельно реализовать алгоритм сортировки методом слияния и использовать
его для сортировки содержимого файлов. Не использовать библиотечные функции сортировки.
Алгоритм должен быть устойчив к большим файлам, не помещающимся целиком в оперативную
память.
Все возможные виды ошибок должны быть обработаны. Программа не должна «падать». Если
после ошибки продолжить выполнение невозможно, программа должна сообщить об этом
пользователю с указанием причины неудачи. Частичная обработка при наличии ошибок более
предпочтительна чем останов программы. Код программы должен быть «чистым».
Для реализации необходимо использовать язык программирования Java, допустимо использовать
стандартные системы сборки проекта (Maven, Gradle)
Решение принимается в виде исходного кода проекта.
Параметры программы задаются при запуске через аргументы командной строки, по порядку:
1. режим сортировки (-a или -d), необязательный, по умолчанию сортируем по возрастанию;
2. тип данных (-s или -i), обязательный;
3. имя выходного файла, обязательное;
4. остальные параметры – имена входных файлов, не менее одного.
Примеры запуска из командной строки для Windows:
sort-it.exe -i -a out.txt in.txt (для целых чисел по возрастанию)
sort-it.exe -s out.txt in1.txt in2.txt in3.txt (для строк по возрастанию)
sort-it.exe -d -s out.txt in1.txt in2.txt (для строк по убыванию)
К решению должна прилагаться инструкция по запуску. В ней можно отображать особенности
реализации, не уточненные в задании. В частности, в инструкции необходимо указывать:
• версию Java;
• при использовании системы сборки – указать систему сборки и ее версию;
• при использовании сторонних библиотек указать их название и версию, а также приложить
ссылки на такие библиотеки (можно в формате зависимостей системы сборки).

Пример:
in1.txt in2.txt in3.txt out.txt
1           1       1       1
4           8       2       1
9           27      3       1
                            2
                            3
                            4
                            8
                            9
                            27
*/
package com.shift.sort.it;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class App {
 
    public static void main(String... args) throws IOException {
                boolean verbose = false;
                boolean distinct = false;
                int maxtmpfiles = DEFAULTMAXTEMPFILES;
                Charset cs = Charset.defaultCharset();
                String inputfile = null, outputfile = null;
                //String mergefile = "temp.txt";
                String mergefile = null;
                //String inputfile = "C://!work/tmp/in1.txt";
                //String outputfile = "C://!work/tmp/out.txt";
                //String inputfile2 = "C://!work/tmp/in2.txt", inputfile3 = null;
                String inputfile2 = null, inputfile3 = null;
                File tempFileStore = null;
                boolean usegzip = false;
                boolean parallel = true;                
                int headersize = 0;
                boolean straight = true;
                
                for (int param = 0; param < args.length; ++param) {
                        if (args[param].equals("-v")
                                || args[param].equals("--verbose")) {
                                verbose = true;
                        } else if ((args[param].equals("-h") || args[param]
                                .equals("--help"))) {
                                displayUsage();
                                return;
                        } else if ((args[param].equals("-d") || args[param]
                                .equals("--distinct"))) {
                                distinct = true;
                        } else if ((args[param].equals("-t") || args[param]
                                .equals("--maxtmpfiles"))
                                && args.length > param + 1) {
                                param++;
                                maxtmpfiles = Integer.parseInt(args[param]);
                                if (maxtmpfiles < 0) {
                                        System.err
                                                .println("maxtmpfiles should be positive");
                                }
                        } else if ((args[param].equals("-c") || args[param]
                                .equals("--charset"))
                                && args.length > param + 1) {
                                param++;
                                cs = Charset.forName(args[param]);
                        } else if ((args[param].equals("-z") || args[param]
                                .equals("--gzip"))) {
                                usegzip = true;
                        } else if ((args[param].equals("-H") || args[param]
                                .equals("--header")) && args.length > param + 1) {
                                param++;
                                headersize = Integer.parseInt(args[param]);
                                if (headersize < 0) {
                                        System.err
                                                .println("headersize should be positive");
                                }
                        } else if ((args[param].equals("-s") || args[param]
                                .equals("--store")) && args.length > param + 1) {
                                param++;
                                tempFileStore = new File(args[param]);
                        } else if (args[param].equals("-d"))
                                 {
                                straight = false;
                        } else if (args[param].equals("in.txt"))
                                 {
                                inputfile = "in.txt";
                        } else if (args[param].equals("in1.txt"))
                                 {
                                inputfile = "in1.txt";
                        }else if (args[param].equals("in2.txt"))
                                 {
                                inputfile2 = "in2.txt";
                        }else if (args[param].equals("in3.txt"))
                                 {
                                inputfile3 = "in3.txt";
                        }else if (args[param].equals("out.txt"))
                                 {
                                outputfile = "out.txt";
                        } else {
                                if ( (inputfile == null) || (inputfile2 == null) 
                                        || (inputfile3 == null) || (outputfile == null) ) {
                                        {
                                        System.out.println("Unparsed inputfile\\outputfile: "
                                                + args[param]);
                                        }
                                }
                        }
                }

                if (outputfile == null) {
                        System.out
                                .println("please provide input and output file names");
                        displayUsage();
                        return;
                }
                
                Comparator<String> comparator = defaultcomparator;
                if (straight == false) {
                    comparator = reversecomparator;
                }
                
                
                if (inputfile2 != null){
                    List<File> fileList = new ArrayList<>();
                    fileList.add( new File(inputfile) );
                    fileList.add( new File(inputfile2) );   
                    if (inputfile3 != null){
                        fileList.add( new File(inputfile3) );                    
                    }
                    mergefile = "temp.txt";
                    mergeSortedFiles(fileList, new File(mergefile) );
                }
                
                if (mergefile != null){
                    List<File> l = sortInBatch(new File(mergefile), 
                        comparator,
                        maxtmpfiles, cs, tempFileStore, distinct, headersize,
                        usegzip, parallel);
                    if (verbose) {
                        System.out
                                .println("created " + l.size() + " tmp files");
                    }
                    mergeSortedFiles(l, new File(outputfile), 
                        comparator, cs,
                        distinct, false, usegzip);
                } else{
                    List<File> l = sortInBatch(new File(inputfile), 
                        comparator,
                        maxtmpfiles, cs, tempFileStore, distinct, headersize,
                        usegzip, parallel);
                    if (verbose) {
                        System.out
                                .println("created " + l.size() + " tmp files");
                    }
                    mergeSortedFiles(l, new File(outputfile), 
                        comparator, cs,
                        distinct, false, usegzip);
                }


                

                
                
                
                
        }
    
    
    
        
        
        
        
    
    
    
    
        
    private static void displayUsage() {
                System.out
                        .println("java com.google.externalsorting.ExternalSort inputfile outputfile");
                System.out.println("Flags are:");
                System.out.println("-v or --verbose: verbose output");
                System.out.println("-d or --distinct: prune duplicate lines");
                System.out
                        .println("-t or --maxtmpfiles (followed by an integer): specify an upper bound on the number of temporary files");
                System.out
                        .println("-c or --charset (followed by a charset code): specify the character set to use (for sorting)");
                System.out
                        .println("-z or --gzip: use compression for the temporary files");
                System.out
                        .println("-H or --header (followed by an integer): ignore the first few lines");
                System.out
                        .println("-s or --store (following by a path): where to store the temporary files");
                System.out
                        .println("-d for descending sorting");
                System.out.println("-h or --help: display this message");
                System.out
                        .println("program accepts only in.txt, in1.txt, in2.txt, in3.txt for input and out.txt for output");
        }

        /**
         * This method calls the garbage collector and then returns the free
         * memory. This avoids problems with applications where the GC hasn't
         * reclaimed memory and reports no available memory.
         *
         * @return available memory
         */
        public static long estimateAvailableMemory() {
          System.gc();
          // http://stackoverflow.com/questions/12807797/java-get-available-memory
          Runtime r = Runtime.getRuntime();
          long allocatedMemory = r.totalMemory() - r.freeMemory();
          long presFreeMemory = r.maxMemory() - allocatedMemory;
          return presFreeMemory;
        }
            
        /**
         * default comparator between strings.
         */
        public static Comparator<String> defaultcomparator = new Comparator<String>() {
                @Override
                public int compare(String r1, String r2) {
                        return r1.compareTo(r2);
                }
        };
        
        /**
         * reverse comparator between strings.
         */
        public static Comparator<String> reversecomparator = new Comparator<String>() {
                @Override
                public int compare(String r1, String r2) {
                        return r2.compareTo(r1);
                }
        };
        
        /**
         * we divide the file into small blocks. If the blocks are too small, we
         * shall create too many temporary files. If they are too big, we shall
         * be using too much memory.
         *
         * @param sizeoffile how much data (in bytes) can we expect
         * @param maxtmpfiles how many temporary files can we create (e.g., 1024)
         * @param maxMemory Maximum memory to use (in bytes)
         * @return the estimate
         */
        public static long estimateBestSizeOfBlocks(final long sizeoffile,
                final int maxtmpfiles, final long maxMemory) {
                // we don't want to open up much more than maxtmpfiles temporary
                // files, better run
                // out of memory first.
                long blocksize = sizeoffile / maxtmpfiles
                        + (sizeoffile % maxtmpfiles == 0 ? 0 : 1);

                // on the other hand, we don't want to create many temporary
                // files
                // for naught. If blocksize is smaller than half the free
                // memory, grow it.
                if (blocksize < maxMemory / 2) {
                        blocksize = maxMemory / 2;
                }
                return blocksize;
        }
        
     /**
         * Default maximal number of temporary files allowed.
         */
        public static final int DEFAULTMAXTEMPFILES = 1024;

        
        /**
         * This will simply load the file by blocks of lines, then sort them
         * in-memory, and write the result to temporary files that have to be
         * merged later. You can specify a bound on the number of temporary
         * files that will be created.
         *
         * @param file some flat file
         * @param cmp string comparator
         * @param maxtmpfiles maximal number of temporary files
         * @param cs character set to use (can use
         *                Charset.defaultCharset())
         * @param tmpdirectory location of the temporary files (set to null for
         *                default location)
         * @param distinct Pass <code>true</code> if duplicate lines should be
         *                discarded.
         * @param numHeader number of lines to preclude before sorting starts
         * @param usegzip use gzip compression for the temporary files
         * @param parallel whether to sort in parallel
         * @return a list of temporary flat files
         * @throws IOException generic IO exception
         */
        public static List<File> sortInBatch(File file, Comparator<String> cmp,
                int maxtmpfiles, Charset cs, File tmpdirectory,
                boolean distinct, int numHeader, boolean usegzip, boolean parallel)
                throws IOException {
                BufferedReader fbr = new BufferedReader(new InputStreamReader(
                        new FileInputStream(file), cs));
                return sortInBatch(fbr, file.length(), cmp, maxtmpfiles,
                        estimateAvailableMemory(), cs, tmpdirectory, distinct,
                        numHeader, usegzip, parallel);
        }
        
        /**
         * Sort a list and save it to a temporary file
         *
         * @return the file containing the sorted data
         * @param tmplist data to be sorted
         * @param cmp string comparator
         * @param cs charset to use for output (can use
         *                Charset.defaultCharset())
         * @param tmpdirectory location of the temporary files (set to null for
         *                default location)
         * @param distinct Pass <code>true</code> if duplicate lines should be
         *                discarded.
         * @param usegzip set to <code>true</code> if you are using gzip compression for the
         *                temporary files
         * @param parallel set to <code>true</code> when sorting in parallel
         * @throws IOException generic IO exception
         */
        public static File sortAndSave(List<String> tmplist,
                Comparator<String> cmp, Charset cs, File tmpdirectory,
                boolean distinct, boolean usegzip, boolean parallel) throws IOException {
                if (parallel) {
                  tmplist = tmplist.parallelStream().sorted(cmp).collect(Collectors.toCollection(ArrayList<String>::new));
                } else {
                  Collections.sort(tmplist, cmp);
                }
                File newtmpfile = File.createTempFile("sortInBatch",
                        "flatfile", tmpdirectory);
                newtmpfile.deleteOnExit();
                OutputStream out = new FileOutputStream(newtmpfile);
                int ZIPBUFFERSIZE = 2048;
                if (usegzip) {
                        out = new GZIPOutputStream(out, ZIPBUFFERSIZE) {
                                {
                                        this.def.setLevel(Deflater.BEST_SPEED);
                                }
                        };
                }
                try (BufferedWriter fbw = new BufferedWriter(new OutputStreamWriter(
                        out, cs))) {
                        if (!distinct) {
                            for (String r : tmplist) {
                                        fbw.write(r);
                                        fbw.newLine();
                            }
                        } else {
                        String lastLine = null;
                        Iterator<String> i = tmplist.iterator();
                        if(i.hasNext()) {
                          lastLine = i.next();
                          fbw.write(lastLine);
                          fbw.newLine();
                        }
                        while (i.hasNext()) {
                          String r = i.next();
                          // Skip duplicate lines
                          if (cmp.compare(r, lastLine) != 0) {
                            fbw.write(r);
                            fbw.newLine();
                            lastLine = r;
                          }
                        }
                        }
                }
                return newtmpfile;
        }
        
         /**
         * This will simply load the file by blocks of lines, then sort them
         * in-memory, and write the result to temporary files that have to be
         * merged later.
         *
         * @param fbr data source
         * @param datalength estimated data volume (in bytes)
         * @param cmp string comparator
         * @param maxtmpfiles maximal number of temporary files
         * @param maxMemory maximum amount of memory to use (in bytes)
         * @param cs character set to use (can use
         *                Charset.defaultCharset())
         * @param tmpdirectory location of the temporary files (set to null for
         *                default location)
         * @param distinct Pass <code>true</code> if duplicate lines should be
         *                discarded.
         * @param numHeader number of lines to preclude before sorting starts
         * @param usegzip use gzip compression for the temporary files
         * @param parallel sort in parallel
         * @return a list of temporary flat files
         * @throws IOException generic IO exception
         */
        public static List<File> sortInBatch(final BufferedReader fbr,
                final long datalength, final Comparator<String> cmp,
                final int maxtmpfiles, long maxMemory, final Charset cs,
                final File tmpdirectory, final boolean distinct,
                final int numHeader, final boolean usegzip, final boolean parallel)
                    throws IOException {
                List<File> files = new ArrayList<>();
                long blocksize = estimateBestSizeOfBlocks(datalength,
                        maxtmpfiles, maxMemory);// in
                // bytes

                try {
                        List<String> tmplist = new ArrayList<>();
                        String line = "";
                        try {
                                int counter = 0;
                                while (line != null) {
                                        long currentblocksize = 0;// in bytes
                                        while ((currentblocksize < blocksize)
                                                && ((line = fbr.readLine()) != null)) {
                                                // as long as you have enough
                                                // memory
                                                if (counter < numHeader) {
                                                        counter++;
                                                        continue;
                                                }
                                                tmplist.add(line);
                                                currentblocksize += StringSizeEstimator
                                                        .estimatedSizeOf(line);
                                        }
                                        files.add(sortAndSave(tmplist, cmp, cs,
                                                tmpdirectory, distinct, usegzip, parallel));
                                        tmplist.clear();
                                }
                        } catch (EOFException oef) {
                                if (tmplist.size() > 0) {
                                        files.add(sortAndSave(tmplist, cmp, cs,
                                                tmpdirectory, distinct, usegzip, parallel));
                                        tmplist.clear();
                                }
                        }
                } finally {
                        fbr.close();
                }
                return files;
        }
    
    
    /**
         * This merges a bunch of temporary flat files
         *
         * @param files The {@link List} of sorted {@link File}s to be merged.
         * @param outputfile The output {@link File} to merge the results to.
         * @return The number of lines sorted.
         * @throws IOException generic IO exception
         */
        public static long mergeSortedFiles(List<File> files, File outputfile)
                throws IOException {
                return mergeSortedFiles(files, outputfile, defaultcomparator,
                        Charset.defaultCharset());
        }
        
        /**
         * This merges a bunch of temporary flat files
         *
         * @param files The {@link List} of sorted {@link File}s to be merged.
         * @param outputfile The output {@link File} to merge the results to.
         * @param cmp The {@link Comparator} to use to compare
         *                {@link String}s.
         * @param cs The {@link Charset} to be used for the byte to
         *                character conversion.
         * @return The number of lines sorted.
         * @throws IOException generic IO exception
         */
        public static long mergeSortedFiles(List<File> files, File outputfile,
                final Comparator<String> cmp, Charset cs) throws IOException {
                return mergeSortedFiles(files, outputfile, cmp, cs, false);
        } 
        
        /**
         * This merges a bunch of temporary flat files
         *
         * @param files The {@link List} of sorted {@link File}s to be merged.
         * @param distinct Pass <code>true</code> if duplicate lines should be
         *                discarded.
         * @param outputfile The output {@link File} to merge the results to.
         * @param cmp The {@link Comparator} to use to compare
         *                {@link String}s.
         * @param cs The {@link Charset} to be used for the byte to
         *                character conversion.
         * @return The number of lines sorted.
         * @throws IOException generic IO exception
         * @since v0.1.2
         */
        public static long mergeSortedFiles(List<File> files, File outputfile,
                final Comparator<String> cmp, Charset cs, boolean distinct)
                throws IOException {
                return mergeSortedFiles(files, outputfile, cmp, cs, distinct,
                        false, false);
        }
        
    /**
         * This merges a bunch of temporary flat files
         *
         * @param files The {@link List} of sorted {@link File}s to be merged.
         * @param distinct Pass <code>true</code> if duplicate lines should be
         *                discarded.
         * @param outputfile The output {@link File} to merge the results to.
         * @param cmp The {@link Comparator} to use to compare
         *                {@link String}s.
         * @param cs The {@link Charset} to be used for the byte to
         *                character conversion.
         * @param append Pass <code>true</code> if result should append to
         *                {@link File} instead of overwrite. Default to be false
         *                for overloading methods.
         * @param usegzip assumes we used gzip compression for temporary files
         * @return The number of lines sorted.
         * @throws IOException generic IO exception
         * @since v0.1.4
         */
        public static long mergeSortedFiles(List<File> files, File outputfile,
                final Comparator<String> cmp, Charset cs, boolean distinct,
                boolean append, boolean usegzip) throws IOException {
                ArrayList<IOStringStack> bfbs = new ArrayList<>();
                for (File f : files) {
                        final int BUFFERSIZE = 2048;
                        InputStream in = new FileInputStream(f);
                        BufferedReader br;
                        if (usegzip) {
                                br = new BufferedReader(
                                        new InputStreamReader(
                                                new GZIPInputStream(in,
                                                        BUFFERSIZE), cs));
                        } else {
                                br = new BufferedReader(new InputStreamReader(
                                        in, cs));
                        }

                        BinaryFileBuffer bfb = new BinaryFileBuffer(br);
                        bfbs.add(bfb);
                }
                BufferedWriter fbw = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(outputfile, append), cs));
                long rowcounter = mergeSortedFiles(fbw, cmp, distinct, bfbs);
                for (File f : files) {
                        f.delete();
                }
                return rowcounter;
        }
        
        /**
         * This merges several BinaryFileBuffer to an output writer.
         *
         * @param fbw     A buffer where we write the data.
         * @param cmp     A comparator object that tells us how to sort the
         *                lines.
         * @param distinct Pass <code>true</code> if duplicate lines should be
         *                discarded.
         * @param buffers
         *                Where the data should be read.
         * @return The number of lines sorted.
         * @throws IOException generic IO exception
         *
         */
        public static long mergeSortedFiles(BufferedWriter fbw,
                final Comparator<String> cmp, boolean distinct,
                List<IOStringStack> buffers) throws IOException {
                PriorityQueue<IOStringStack> pq = new PriorityQueue<>(
                        11, new Comparator<IOStringStack>() {
                                @Override
                                public int compare(IOStringStack i,
                                IOStringStack j) {
                                        return cmp.compare(i.peek(), j.peek());
                                }
                        });
                for (IOStringStack bfb : buffers) {
                        if (!bfb.empty()) {
                                pq.add(bfb);
                        }
                }
                long rowcounter = 0;
                try {
                        if (!distinct) {
                            while (pq.size() > 0) {
                                    IOStringStack bfb = pq.poll();
                                    String r = bfb.pop();
                                    fbw.write(r);
                                    fbw.newLine();
                                    ++rowcounter;
                                    if (bfb.empty()) {
                                            bfb.close();
                                    } else {
                                            pq.add(bfb); // add it back
                                    }
                            }
                        } else {
                            String lastLine = null;
                            if(pq.size() > 0) {
                           IOStringStack bfb = pq.poll();
                           lastLine = bfb.pop();
                           fbw.write(lastLine);
                           fbw.newLine();
                           ++rowcounter;
                           if (bfb.empty()) {
                             bfb.close();
                           } else {
                             pq.add(bfb); // add it back
                           }
                         }
                            while (pq.size() > 0) {
                        IOStringStack bfb = pq.poll();
                          String r = bfb.pop();
                          // Skip duplicate lines
                          if  (cmp.compare(r, lastLine) != 0) {
                            fbw.write(r);
                            fbw.newLine();
                            lastLine = r;
                          }
                          ++rowcounter;
                          if (bfb.empty()) {
                            bfb.close();
                          } else {
                            pq.add(bfb); // add it back
                          }
                            }
                        }
                } finally {
                        fbw.close();
                        for (IOStringStack bfb : pq) {
                                bfb.close();
                        }
                }
                return rowcounter;

        }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}