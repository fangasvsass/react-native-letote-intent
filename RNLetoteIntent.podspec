
Pod::Spec.new do |s|
  s.name         = "RNLetoteIntent"
  s.version      = "1.5.6"
  s.summary      = "RNLetoteIntent"
  s.description  = <<-DESC
                  RNLetoteIntent
                   DESC
  s.homepage     = ""
  s.license      = "MIT"
  # s.license      = { :type => "MIT", :file => "FILE_LICENSE" }
  s.author             = { "author" => "author@domain.cn" }
  s.platform     = :ios, "9.0"
  s.source       = { :git => "https://github.com/author/RNLetoteIntent.git", :tag => "master" }
  s.source_files  = "ios/**/*.{h,m}"
  s.requires_arc = true


  s.dependency "React"
  #s.dependency "others"

end

  